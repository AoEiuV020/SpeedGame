package cc.aoeiuv020.speed

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.27-19:16:15.
 */
class Barrier(private val screen: GameScreen, private val hero: Hero) : Group(), Disposable {
    companion object {
        val ORIGINAL_SPEED = 400
    }

    private val blockList = mutableListOf<Block>()

    init {
        setSize(400f, 400f / 5)
        add(Block())
        add(Block())
        add(Block())
    }

    private fun add(block: Block) {
        blockList.add(block)
        addActor(block)
    }

    fun reset() {
        if (y < height) {
            y += ((stage.height - (y + height)) / 80).toInt() * 80f
            shuffle()
        }
    }

    private fun shuffle() {
        children.forEach { block ->
            block.x = block.width * MathUtils.random(0, 4)
        }
    }

    override fun act(delta: Float) {
        super.act(delta)

        val dY = ORIGINAL_SPEED * delta
        move(dY)
        while (y + height < 0f) {
            y += stage.height.let { it - it % 80 + 80 }
            shuffle()
        }
    }

    private fun move(dY: Float) {
        if (hero.top in y - dY..y) {
            children.forEach { block ->
                if (block.x < hero.right && hero.x < block.right) {
                    y = hero.top
                    screen.gameOver()
                    return
                }
            }
        }
        y -= dY
    }

    override fun dispose() {
        blockList.forEach {
            it.dispose()
        }
    }
}
