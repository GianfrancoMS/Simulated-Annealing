package com.gianfranco.simulatedannealing.presenter;

import com.gianfranco.simulatedannealing.model.Result;

public final class Summary {
    private String iterations;
    private String replacements;
    private String temperature;
    private String distance;

    public void setResult(Result result) {
        this.iterations = Integer.toString(result.iterations());
        this.replacements = Integer.toString(result.replacements());
        this.temperature = Double.toString(result.temperature());
        this.distance = Double.toString(result.distance() / 1000);
    }

    public String iterations() {
        return iterations;
    }

    public String replacements() {
        return replacements;
    }

    public String temperature() {
        return temperature;
    }

    public String distance() {
        return distance;
    }
}
