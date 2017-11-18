package cc.aoeiuv020.supersonicspeedcar

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.scenes.scene2d.Stage

/**
 *
 * Created by AoEiuV020 on 2017.11.18-16:46:34.
 */
class GameStage : Stage() {
    companion object {
        private val TAG = GameStage::class.java.simpleName
    }

    private val car = Car()
    private val background = Background(viewport)
    private val listener = Listener()

    init {

        car.setCenterPosition(width / 2, car.height / 2)

        addActor(background)
        addActor(car)

        Gdx.input.inputProcessor = listener
    }

    override fun act(delta: Float) {
        (car.speed / 2).let {
            camera.position.y += it
            car.moveBy(0f, it)
        }


        background.update(camera)

        super.act(delta)
    }

    override fun dispose() {
        super.dispose()
        background.dispose()
        car.dispose()
    }

    inner class Listener : InputAdapter() {

        override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
            car.moveBy(Gdx.input.getDeltaX(pointer).toFloat(), -Gdx.input.getDeltaY(pointer).toFloat())
            return super.touchDragged(screenX, screenY, pointer)
        }
    }
}