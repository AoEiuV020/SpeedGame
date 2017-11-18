package cc.aoeiuv020.supersonicspeedcar

import com.badlogic.gdx.Game

class SupersonicSpeedCarGdxGame : Game() {
    private lateinit var gameScreen: GameScreen

    override fun create() {
        gameScreen = GameScreen()
        setScreen(gameScreen)

    }

}
