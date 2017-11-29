package cc.aoeiuv020.speed

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable

/**
 * Created by AoEiuV020 on 2017.11.30-02:15:06.
 */
class SeekBar(min: Float, max: Float, stepSize: Float, vertical: Boolean)
    : ProgressBar(min, max, stepSize, vertical, style) {
    companion object : Disposable {
        private val background: Texture
        private val style: ProgressBarStyle
        private val knob: Texture

        init {
            val p = Pixmap(21, 21, Pixmap.Format.RGB888).apply {
                setColor(Color.DARK_GRAY)
                fill()
            }
            background = Texture(p)
            p.apply {
                setColor(Color.YELLOW)
                fill()
            }
            knob = Texture(p)
            p.dispose()
            style = ProgressBarStyle(TextureRegionDrawable(TextureRegion(background)), TextureRegionDrawable(TextureRegion(knob)))
        }

        override fun dispose() {
            background.dispose()
        }
    }
}