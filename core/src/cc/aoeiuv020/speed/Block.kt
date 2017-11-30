package cc.aoeiuv020.speed

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.27-19:23:54.
 */
class Block : Image(), Disposable {
    private val texture: Texture

    init {
        val p = Pixmap(111, 111, Pixmap.Format.RGBA8888).apply {
            setColor(Color.RED)
            fillRectangle(0, 0, width, height)
        }
        texture = Texture(p)
        p.dispose()
        drawable = TextureRegionDrawable(TextureRegion(texture))
        setSize(80f, 80f)
    }

    override fun dispose() {
        texture.dispose()
    }
}