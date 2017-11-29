package cc.aoeiuv020.speed

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable

/**
 * Created by AoEiuV020 on 2017.11.30-02:00:05.
 */
class StartButton : Image(texture) {
    companion object : Disposable {
        private val texture: Texture

        init {
            val p = Pixmap(111, 111, Pixmap.Format.RGBA8888).apply {
                setColor(Color.WHITE)
                fillCircle(width / 2, height / 2, minOf(width, height) / 2)
                setColor(Color.BLACK)
                fillCircle(width / 2, height / 2, minOf(width, height) / 2 - 10)
                setColor(Color.WHITE)
                val x = width / 5 * 2
                val x2 = width / 10 * 7
                val h = height / 5
                fillTriangle(x, height / 2 - h, x, height / 2 + h, x2, height / 2)
            }
            texture = Texture(p)
            p.dispose()
        }

        override fun dispose() {
            texture.dispose()
        }
    }

    init {
        setSize(80f, 80f)
    }
}