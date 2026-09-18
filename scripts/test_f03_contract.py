import json
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


class F03ContractTest(unittest.TestCase):
    def test_migration_fixture_preserves_project_relations(self):
        value = json.loads((ROOT / "fixtures/f03_migration_v1.json").read_text(encoding="utf-8"))
        project_ids = {item["projectId"] for item in value["projects"]}
        self.assertTrue(project_ids)
        for collection in ("drafts", "events"):
            self.assertTrue(all(item["projectId"] in project_ids for item in value[collection]))

    def test_repository_is_transactional_and_project_scoped(self):
        source = (ROOT / "app/src/main/java/com/playertwo/ideas/data/ProjectRepository.java").read_text(encoding="utf-8")
        self.assertIn("runInTransaction", source)
        self.assertIn("project.projectId", source)
        self.assertIn("deletePermanently(String projectId)", source)

    def test_schema_fixture_is_versioned(self):
        self.assertTrue((ROOT / "app/schemas/com.playertwo.ideas.data.IdeaDatabase/1.json").exists())
        self.assertEqual(json.loads((ROOT / "fixtures/f03_migration_v1.json").read_text())["schemaVersion"], 1)


if __name__ == "__main__":
    unittest.main()
