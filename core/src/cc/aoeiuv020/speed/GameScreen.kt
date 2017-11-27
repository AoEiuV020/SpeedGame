package cc.aoeiuv020.speed

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 *
 * Created by AoEiuV020 on 2017.11.18-16:44:53.
 */
class GameScreen : ScreenAdapter() {
    private val controlViewPort: Viewport = StretchViewport(1080f, 720f)
    private val gameViewPort: Viewport = FitViewport(400f, 720f)
    private val background: Background = Background()
    private val hero: Hero = Hero()
    private val controlStage: Stage = Stage(controlViewPort)
    private val gameStage: Stage = Stage(gameViewPort)
    private var movementMultiple = 2f
    private var speedMultiple = 2f

    init {
        gameStage.addActor(background)
        gameStage.addActor(hero)

        val barrier = Barrier()
        barrier.y = gameStage.height.let { it - it % 80 }
        gameStage.addActor(barrier)

        Gdx.input.inputProcessor = controlStage
        controlStage.addListener(object : InputListener() {
            private var deltaX = 0f
            private var deltaY = 0f
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                deltaX = x
                deltaY = y
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                deltaX = x - deltaX
                deltaY = y - deltaY
                hero.moveBy(movementMultiple * deltaX, movementMultiple * deltaY)
                deltaX = x
                deltaY = y
            }
        })
    }

    override fun resize(width: Int, height: Int) {
        gameViewPort.update(width, height)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        gameStage.act(speedMultiple * delta)
        gameStage.draw()
    }

    override fun hide() {
        controlStage.dispose()
        gameStage.dispose()
        background.dispose()
        Hero.dispose()
        Block.dispose()
    }
}