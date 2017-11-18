package cc.aoeiuv020.supersonicspeedcar

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector3
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

    init {

        addActor(background)
        addActor(car)

    }

    override fun act(delta: Float) {
        Gdx.input.inputProcessor = Listener()


        camera.position.y += delta * 100

        background.update(camera)


        super.act(delta)
    }

    private fun move(x: Float, y: Float) {
        car.setPosition(x, y)
    }

    override fun dispose() {
        super.dispose()
        background.dispose()
        car.dispose()
    }

    inner class Listener : InputAdapter() {
        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            val v = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
            move(v.x, v.y)
            return super.touchDown(screenX, screenY, pointer, button)
        }

        override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
            val v = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
            move(v.x, v.y)
            return super.touchDragged(screenX, screenY, pointer)
        }
    }
}