#!/usr/bin/env python3
"""Deterministic F00 structural and semantic contract checks."""
import json, sys
from pathlib import Path
from jsonschema import Draft202012Validator, FormatChecker

ROOT = Path(__file__).resolve().parents[1]
SCHEMAS = {p.name.replace('.schema.json',''): json.loads(p.read_text()) for p in (ROOT/'schemas').glob('*.json')}

def load(path): return json.loads((ROOT/path).read_text())
def structural(schema_name, value):
    schema = SCHEMAS[schema_name]
    Draft202012Validator.check_schema(schema)
    errors = sorted(Draft202012Validator(schema, format_checker=FormatChecker()).iter_errors(value), key=lambda e: list(e.path))
    return [f"{'.'.join(map(str,e.path)) or '$'}: {e.message}" for e in errors]

def semantic(p):
    errs=[]; ids=[]; decisions={}
    for d in p.get('decisions',[]):
        if d['id'] in ids: errs.append('VAL-001 duplicate id '+d['id'])
        ids.append(d['id']); decisions[d['id']]=d
        if d.get('state')=='LOCKED' and (d.get('authority')!='USER' or d.get('lockAction')!='HUMAN_EXPLICIT'):
            errs.append('VAL-003 AI/non-human authority cannot create LOCKED decision '+d['id'])
    for r in p.get('requirements',[]):
        if r['id'] in ids: errs.append('VAL-001 duplicate id '+r['id'])
        ids.append(r['id'])
        for ref in r.get('decisionRefs',[]):
            if ref not in decisions: errs.append('VAL-002 missing reference '+ref)
    return errs

def expect(path, code):
    p=load(path); errs=structural('project',p)+semantic(p)
    if not any(code in e for e in errs): raise SystemExit(f'EXPECTED {code} NOT FOUND in {path}: {errs}')
    print(f'PASS expected {code}: {path}')

def main():
    for name in ('project','manifest','ai-response'):
        Draft202012Validator.check_schema(SCHEMAS[name]); print('PASS schema syntax', name)
    for path in ('fixtures/light-simple.project.json','fixtures/standard-medium.project.json','fixtures/deep.project.json'):
        p=load(path); errs=structural('project',p)+semantic(p)
        if errs: raise SystemExit(f'VALID fixture failed {path}: {errs}')
        print('PASS valid fixture', path)
    for schema_name,path in (('manifest','fixtures/valid.manifest.json'),('ai-response','fixtures/valid.ai-response.json')):
        errs=structural(schema_name,load(path))
        if errs: raise SystemExit(f'VALID {schema_name} failed: {errs}')
        print('PASS valid', path)
    expect('fixtures/invalid-duplicate-id.project.json','VAL-001')
    expect('fixtures/invalid-missing-ref.project.json','VAL-002')
    expect('fixtures/invalid-ai-locked.project.json','VAL-003')
    print('PASS semantic validation suite')
if __name__=='__main__': main()
