package cc.aoeiuv020.speed

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Pool

/**
 *
 * Created by AoEiuV020 on 2017.11.27-19:16:15.
 */
class Barrier(private val pool: BarrierPool) : Group(), Pool.Poolable {

    init {
        setSize(400f, 400f / 5)
        addActor(Block())
        addActor(Block())
        addActor(Block())
    }

    override fun reset() {
        children.forEach { block ->
            block.x = block.width * MathUtils.random(0, 4)
        }
    }

    override fun act(delta: Float) {
        super.act(delta)

        y -= 800f * delta

        if (y + height < 0f) {
            stage.root.removeActor(this)
            pool.remove(this)
        }
    }
}

class BarrierPool : Pool<Barrier>(), Disposable {
    override fun newObject(): Barrier {
        return Barrier(this)
    }

    private val barrierList = mutableListOf<Barrier>()

    override fun dispose() {
        val list = ArrayList(barrierList)
        barrierList.clear()
        list.forEach {
            it.stage.root.removeActor(it)
            free(it)
        }
    }

    fun new(): Barrier = obtain().also {
        barrierList.add(it)
    }

    fun remove(block: Barrier) {
        barrierList.remove(block)
        free(block)
    }
}