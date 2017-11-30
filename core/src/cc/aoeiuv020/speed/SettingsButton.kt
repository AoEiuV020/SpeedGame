package cc.aoeiuv020.speed

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.30-03:10:08.
 */
class SettingsButton : Image(), Disposable {
    private val texture: Texture = Texture("settings.png")

    init {
        drawable = TextureRegionDrawable(TextureRegion(texture))
        setSize(80f, 80f)
    }

    override fun dispose() {
        texture.dispose()
    }
}