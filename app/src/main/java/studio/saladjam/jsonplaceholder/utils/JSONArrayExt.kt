package studio.saladjam.jsonplaceholder.utils

import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.toMutableList(): MutableList<JSONObject> = MutableList(length(), this::getJSONObject)
