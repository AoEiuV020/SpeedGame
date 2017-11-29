package cc.aoeiuv020.speed

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
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
    private val start = StartButton()
    private val settings = SettingsButton()
    private val controlStage: Stage = Stage(controlViewPort)
    private val gameOverStage: Stage = Stage()
    private val hintStage: Stage = Stage(controlViewPort)
    private val settingStage: Stage = Stage(gameViewPort)
    private val gameStage: Stage = Stage(gameViewPort)
    private var sensitivity = 1f
    private var maxSpeed = 10f
    private var speedMultiple = 1f
    private var gameRunning = false
    private var isSettingNow = false
    private var isHintNow = false
    private lateinit var tfSensitivity: TextField
    private lateinit var tfMaxSpeed: TextField
    private val gameFont = BitmapFont().apply {
        data.setScale(2f)
    }

    init {

        gameStage.addActor(background)

        gameStage.addActor(barrier)

        gameStage.addActor(hero)

        controlStage.addListener(object : InputListener() {
            private val map: MutableMap<Int, Pair<Vector2, Boolean>> = mutableMapOf()
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val isSpeed = x > controlStage.width / 3 * 2
                map.put(pointer, Vector2(x, y) to isSpeed)
                if (isSpeed) {
                    setSpeed(y / controlStage.height * maxSpeed)
                }
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                val p = map[pointer]!!
                val v = p.first
                v.set(x - v.x, y - v.y)
                if (p.second) {
                    setSpeed(y / controlStage.height * maxSpeed)
                } else {
                    move(sensitivity * v.x)
                }
                v.set(x, y)
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                val p = map[pointer]!!
                if (p.second) {
                    setSpeed(1f)
                }
            }
        })

        start.setCenter(gameStage.width / 2, gameStage.height / 3)
        start.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                gameStart()
            }
        })

        settings.apply {
            setSize(80f, 80f)
            setPosition(gameStage.width - width, gameStage.height - height)
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    gameSetting()
                }
            })
        }

        val ls = Label.LabelStyle().apply {
            font = gameFont
            fontColor = Color.WHITE
        }
        val tfs = TextField.TextFieldStyle().apply {
            font = gameFont
            fontColor = Color.WHITE
            val p = Pixmap(1, 1, Pixmap.Format.RGB888).apply {
                setColor(Color.DARK_GRAY)
                fill()
            }
            // 三个Texture没有销毁，
            background = TextureRegionDrawable(TextureRegion(Texture(p)))
            p.apply {
                setColor(Color.GREEN)
                fill()
            }
            selection = TextureRegionDrawable(TextureRegion(Texture(p)))
            p.apply {
                setColor(Color.WHITE)
                fill()
            }
            cursor = TextureRegionDrawable(TextureRegion(Texture(p)))
            p.dispose()
        }
        settingStage.apply {
            var y = settingStage.height / 10 * 8
            addActor(Label("Sensitivity", ls).apply {
                setPosition(80f, y)
                y -= height + 10f
            })
            tfSensitivity = TextField(sensitivity.toString(), tfs).apply {
                setPosition(80f, y)
                y -= height + 10f
            }
            addActor(tfSensitivity)
            addActor(Label("Max Speed", ls).apply {
                setPosition(80f, y)
                y -= height + 10f
            })
            tfMaxSpeed = TextField(maxSpeed.toString(), tfs).apply {
                setPosition(80f, y)
                y -= height + 10f
            }
            addActor(tfMaxSpeed)
        }

        hintStage.apply {
            addActor(Label("Move", ls).apply {
                setPosition(100f, 100f)
            })
            addActor(Label("Change Speed", ls).apply {
                setPosition(hintStage.height, hintStage.height / 2)
            })
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    isHintNow = false
                    gameViewPort.update(screen.first, screen.second)
                    gameOver()
                }
            })
        }

        barrier.reset()

        hint()
    }

    private fun setSpeed(speed: Float) {
        speedMultiple = speed
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

    private lateinit var screen: Pair<Int, Int>

    override fun resize(width: Int, height: Int) {
        screen = Pair(width, height)
        if (isHintNow) {
            controlViewPort.update(width, height)
        } else {
            gameViewPort.update(width, height)
        }
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

        if (isHintNow) {
            hintStage.draw()
            return
        }
        if (gameRunning && !pause) {
            gameStage.act(speedMultiple * delta)
        }
        gameStage.draw()
        gameOverStage.draw()

        if (isSettingNow) {
            settingStage.draw()
        }
    }

    override fun hide() {
        controlStage.dispose()
        gameStage.dispose()
        background.dispose()
        Hero.dispose()
        Block.dispose()
        StartButton.dispose()
    }

    fun gameStart() {
        Gdx.input.inputProcessor = controlStage
        if (isSettingNow) {
            settingStage.root.removeActor(start)
            try {
                sensitivity = tfSensitivity.text.toFloat()
            } catch (_: Exception) {
            }
            try {
                maxSpeed = tfMaxSpeed.text.toFloat()
            } catch (_: Exception) {
            }
        } else {
            gameStage.root.removeActor(start)
        }
        gameStage.root.removeActor(settings)
        setSpeed(1f)
        barrier.reset()
        gameRunning = true
        isSettingNow = false
    }

    fun gameOver() {
        Gdx.input.inputProcessor = gameStage
        gameStage.addActor(start)
        gameStage.addActor(settings)
        gameRunning = false
    }

    fun gameSetting() {
        Gdx.input.inputProcessor = settingStage
        gameStage.root.removeActor(start)
        settingStage.addActor(start)
        isSettingNow = true
    }

    private fun hint() {
        Gdx.input.inputProcessor = hintStage
        isHintNow = true
    }
}