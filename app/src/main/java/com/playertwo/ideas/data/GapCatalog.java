package com.playertwo.ideas.data;

import java.util.ArrayList;
import java.util.List;

/** Small deterministic catalog; simple projects intentionally receive fewer questions. */
public final class GapCatalog {
    private GapCatalog() { }

    public static List<GapDefinition> forTypeAndMode(String type, String mode) {
        if (type == null || mode == null) throw new IllegalArgumentException("type and mode required");
        ArrayList<GapDefinition> result = new ArrayList<>();
        result.add(new GapDefinition("goal", "CRITICAL", "Qual é o resultado principal?",
            "validar|vender|organizar", "Começar pelo resultado mensurável", "Escopo menor entrega mais rápido", null));
        result.add(new GapDefinition("audience", "IMPORTANT", "Quem usará ou receberá o resultado?",
            "eu|equipe|clientes", "Escolher um público inicial", "Público amplo aumenta decisões", "eu"));
        if ("DEEP".equalsIgnoreCase(mode) || "APP".equalsIgnoreCase(type)) {
            result.add(new GapDefinition("constraints", "CRITICAL", "Quais restrições não podem ser violadas?",
                "offline|privacidade|prazo", "Registrar limites antes de construir", "Cada limite pode reduzir alternativas", null));
            result.add(new GapDefinition("success", "IMPORTANT", "Como saberemos que funcionou?",
                "teste|uso|métrica", "Definir um sinal verificável", "Métrica fraca gera retrabalho", "teste"));
        }
        return result;
    }
}
