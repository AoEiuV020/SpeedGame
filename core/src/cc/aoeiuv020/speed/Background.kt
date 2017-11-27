package cc.aoeiuv020.speed

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.18-19:31:51.
 */
class Background : Actor(), Disposable {
    private val image: Image
    private val texture: Texture
    private val blockHeight = 80

    init {
        setSize(400f, 1000f)

        val p = Pixmap(width.toInt(), 2 * height.toInt(), Pixmap.Format.RGBA8888).apply {
            setColor(Color.WHITE)
            var offset = 0
            while (offset < height) {
                drawLine(0, offset, 0, offset + blockHeight)
                // 不-1会超出画布，
                drawLine(width - 1, offset, width - 1, offset + blockHeight)
                offset += 2 * blockHeight
            }
        }
        texture = Texture(p)
        p.dispose()
        image = Image(TextureRegionDrawable(TextureRegion(texture)))
    }

    private var offset: Float = 0f

    override fun act(delta: Float) {
        offset += Barrier.ORIGINAL_SPEED * delta
        if (offset > blockHeight * 2) {
            offset -= blockHeight * 2
        }
        image.y = -offset
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        image.draw(batch, parentAlpha)
    }

    override fun dispose() {
        texture.dispose()
    }
}