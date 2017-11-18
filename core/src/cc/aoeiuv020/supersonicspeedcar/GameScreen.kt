package cc.aoeiuv020.supersonicspeedcar

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20

/**
 *
 * Created by AoEiuV020 on 2017.11.18-16:44:53.
 */
class GameScreen : ScreenAdapter() {
    companion object {
        private val WIDTH = Gdx.graphics.width
        private val HEIGHT = Gdx.graphics.height
    }

    private val gameStage: GameStage = GameStage()

    init {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        gameStage.act(delta)
        gameStage.draw()
    }

    override fun hide() {
        gameStage.dispose()
    }
}