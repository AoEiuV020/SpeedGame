package cc.aoeiuv020.speed

import com.badlogic.gdx.Screen
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group

/**
 *
 * Created by AoEiuV020 on 2017.11.27-19:16:15.
 */
class Barrier(private val screen: Screen, private val hero: Hero) : Group() {
    companion object {
        val ORIGINAL_SPEED = 400
    }

    init {
        setSize(400f, 400f / 5)
        addActor(Block())
        addActor(Block())
        addActor(Block())
    }

    private fun reset() {
        children.forEach { block ->
            block.x = block.width * MathUtils.random(0, 4)
        }
    }

    override fun act(delta: Float) {
        super.act(delta)

        y -= ORIGINAL_SPEED * delta

        if (y + height < 0f) {
            y += stage.height.let { it - it % 80 + 80 }
            reset()
        }
        judge()
    }

    private fun judge() {
        if (hero.top.between(y, top)) {
            val heroRect = hero.getRectangle()
            children.forEach { block ->
                val blockRect = (block as Block).getRectangle()
                if (blockRect.overlaps(heroRect)) {
                    screen.pause()
                }
            }
        }
    }
}
