package cc.aoeiuv020.speed

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable

/**
 * Created by AoEiuV020 on 2017.11.30-03:10:08.
 */
class SettingsButton : Image(texture) {
    companion object : Disposable {
        private val texture: Texture = Texture("settings.png")

        override fun dispose() {
            texture.dispose()
        }
    }
}