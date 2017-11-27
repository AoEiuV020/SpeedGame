package cc.aoeiuv020.speed

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.18-19:31:51.
 */
class Background : Actor(), Disposable {
    private lateinit var image: Image
    private lateinit var texture: Texture
    override fun setStage(stage: Stage) {
        super.setStage(stage)

        setSize(stage.width, stage.height)

        val p = Pixmap(width.toInt(), height.toInt(), Pixmap.Format.RGBA8888).apply {
            setColor(Color.WHITE)
            drawRectangle(0, 0, width, height)
        }
        texture = Texture(p)
        p.dispose()
        image = Image(TextureRegionDrawable(TextureRegion(texture)))
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        image.draw(batch, parentAlpha)
    }

    override fun dispose() {
        texture.dispose()
    }
}