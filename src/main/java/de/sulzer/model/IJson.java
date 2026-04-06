package de.sulzer.model;

import javax.json.JsonValue;

public interface IJson {

    public String toJSON();

    public JsonValue getAsJson();

}
