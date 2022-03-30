package app.parkee.moviedb.utils

import android.os.Build
import android.os.Bundle
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Reused {

    fun jsonObjectToMapObj(jsonObject: JSONObject): Map<String, Any>? {
        val mapData: MutableMap<String, Any> = HashMap()
        try {
            val keys: Iterator<*> = jsonObject.keys()
            while (keys.hasNext()) {
                // loop to get the dynamic key
                val currentDynamicKey = keys.next() as String
                // get the value of the dynamic key
                val currentDynamicValue = jsonObject[currentDynamicKey]
                // do something here with the value...
                mapData[currentDynamicKey] = currentDynamicValue
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return mapData
    }

    fun mapToBundle(map: Map<String, Any>): Bundle {
        val bundle = Bundle()
        for ((k,v) in map){
            when (v) {
                is Boolean -> {
                    bundle.putBoolean(k, v)
                }
                is Int -> {
                    bundle.putInt(k, v)
                }
                is Double -> {
                    bundle.putDouble(k, v)
                }
                is Float -> {
                    bundle.putFloat(k, v)
                }
                else -> {
                    bundle.putString(k, v.toString())
                }
            }
        }
        return bundle
    }

    fun getVisibleDate(str:String):String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(str, formatter)
            val mFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            date.format(mFormatter)
        } else {
            str
        }
    }

    fun getBaseImageUrl(mode:Int = 0):String {
        if (mode == 1){
            return "https://image.tmdb.org/t/p/w500"
        }
        return "https://image.tmdb.org/t/p/w200"
    }

}