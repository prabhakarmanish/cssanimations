package coded.toolbox.testnavigationscroll

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun loadAnimations(context: Context, filename: String): List<AnimationPreview> {
    // Dynamically load the JSON file based on the filename parameter
    val json = context.assets.open("$filename.json").bufferedReader().use { it.readText() }
    val type = object : TypeToken<AnimationList>() {}.type
    val animations = Gson().fromJson<AnimationList>(json, type)
    return animations.animations
}
