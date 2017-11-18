package cc.aoeiuv020.supersonicspeedcar

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.18-20:48:11.
 */
class Car() : Actor(), Disposable {
    private val texture = Texture("carBlue.png")
    private val image = Image(texture)

    init {
        image.rotation = 90f
        val width = 100f
        val height = 100f / image.height * image.width
        image.height = width
        image.width = height
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        image.x = x + image.height / 2
        image.y = y - image.width / 2
        image.draw(batch, parentAlpha)
    }

    override fun dispose() {
        texture.dispose()
    }

}