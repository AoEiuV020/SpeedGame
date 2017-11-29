package cc.aoeiuv020.speed

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
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
    private val barrier: Barrier = Barrier(this, hero)
    private val controlStage: Stage = Stage(controlViewPort)
    private val gameOverStage: Stage = Stage()
    private val gameStage: Stage = Stage(gameViewPort)
    private var movementMultiple = 2f
    private var speedMultiple = 2f
    private var gameRunning = false

    init {
        gameStage.addActor(background)

        gameStage.addActor(barrier)

        gameStage.addActor(hero)

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
                if (!pause) {
                    move(movementMultiple * deltaX)
                }
                deltaX = x
                deltaY = y
            }

            override fun keyDown(event: InputEvent, keycode: Int): Boolean {
                if ((Input.Keys.NUM_1..Input.Keys.NUM_9).contains(keycode)) {
                    val num = keycode - Input.Keys.NUM_0
                    speedMultiple = num.toFloat()
                }
                return false
            }
        })
        gameOverStage.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                gameStart()
            }
        })
        barrier.reset()
        gameOver()
    }

    private fun move(dX: Float) {
        if (hero.top < barrier.y || hero.y > barrier.top) {
            hero.moveBy(dX, 0f)
            return
        }
        barrier.children.mapNotNull { block ->
            if (dX > 0) {
                block.x.takeIf { it >= hero.right && it <= hero.right + dX }
            } else {
                block.right.takeIf { it <= hero.x && it >= hero.x + dX }
            }
        }.minBy {
            Math.abs(it - hero.x)
        }?.let {
            if (dX > 0) {
                hero.moveBy(it - hero.right, 0f)
            } else {
                hero.moveBy(it - hero.x, 0f)
            }
        } ?: hero.moveBy(dX, 0f)
    }

    override fun resize(width: Int, height: Int) {
        gameViewPort.update(width, height)
    }

    private var pause = false

    override fun pause() {
        pause = true
    }

    override fun resume() {
        pause = false
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (gameRunning) {
            gameStage.act(speedMultiple * delta)
        }
        gameStage.draw()
        gameOverStage.draw()
    }

    override fun hide() {
        controlStage.dispose()
        gameStage.dispose()
        background.dispose()
        Hero.dispose()
        Block.dispose()
    }

    fun gameStart() {
        Gdx.input.inputProcessor = controlStage
        barrier.reset()
        gameRunning = true
    }

    fun gameOver() {
        Gdx.input.inputProcessor = gameOverStage
        gameRunning = false
    }
}