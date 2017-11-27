package cc.aoeiuv020.speed

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.27-18:28:42.
 */
class Hero : Image(texture) {
    companion object : Disposable {
        private val texture: Texture

        init {
            val p = Pixmap(111, 111, Pixmap.Format.RGBA8888).apply {
                setColor(Color.WHITE)
                fillCircle(width / 2, height / 2, minOf(width, height) / 2)
            }
            texture = Texture(p)
            p.dispose()
        }

        override fun dispose() {
            texture.dispose()
        }
    }

    init {
        setSize(20f, 20f)
    }

    private val rect = Rectangle()

    fun getRectangle(): Rectangle {
        rect.set(x, y, width, height)
        return rect
    }

    override fun setStage(stage: Stage?) {
        super.setStage(stage)
        stage?.let {
            setCenter(it.width / 2, height)
        }
    }

    override fun positionChanged() {
        if (x < 0) x = 0f
        if (right > stage.width) x = stage.width - width
        if (top > stage.height) y = stage.height - height
        if (y < 0) y = 0f
    }
}