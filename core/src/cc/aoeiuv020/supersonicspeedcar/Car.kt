package cc.aoeiuv020.supersonicspeedcar

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.18-20:48:11.
 */
class Car : Actor(), Disposable {
    private val texture = Texture("carBlue.png")
    private val image = Image(texture)
    val speed = 36f

    init {
        image.rotation = 90f
        width = 100f
        height = 100f / image.height * image.width
        image.height = width
        image.width = height
    }

    override fun getY(): Float = image.y
    override fun setY(y: Float) {
        image.y = y
    }

    override fun moveBy(x: Float, y: Float) {
        Gdx.app.log("Car", "$y")
        image.moveBy(x, y)
    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        image.draw(batch, parentAlpha)
    }

    fun setCenterPosition(x: Float, y: Float) {
        image.x = x + image.height / 2
        image.y = y - image.width / 2
    }

    override fun dispose() {
        texture.dispose()
    }
}