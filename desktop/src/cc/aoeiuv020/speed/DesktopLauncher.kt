package cc.aoeiuv020.speed

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

/**
 *
 * Created by AoEiuV020 on 2017.11.29-00:15:02.
 */
object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.apply {
            width = 1024
            height = 512
        }
        LwjglApplication(SpeedGame(), config)
    }
}
