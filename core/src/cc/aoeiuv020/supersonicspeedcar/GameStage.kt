package cc.aoeiuv020.supersonicspeedcar

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 *
 * Created by AoEiuV020 on 2017.11.18-16:46:34.
 */
class GameStage : Stage() {
    companion object {
        private val TAG = GameStage::class.java.simpleName
    }

    private val textures = listOf(Texture("badlogic.jpg"),
            Texture("background.jpg"))

    private val actor = Image(textures[0])

    private val background = Background(textures[1])

    init {

        background.init(viewport)

        addActor(background)
        addActor(actor)

    }

    override fun act(delta: Float) {
        Gdx.input.inputProcessor = Listener()


        camera.position.y += delta * 100

        background.update(camera)


        super.act(delta)
    }

    private fun move(x: Float, y: Float) {
        actor.setPosition(x - actor.width / 2, y - actor.height / 2)
    }

    override fun dispose() {
        super.dispose()
        textures.forEach(Texture::dispose)
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