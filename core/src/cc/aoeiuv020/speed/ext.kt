@file:Suppress("unused")

package cc.aoeiuv020.speed

import com.badlogic.gdx.scenes.scene2d.Actor

/**
 *
 * Created by AoEiuV020 on 2017.11.27-18:40:08.
 */
fun Actor.setCenter(x: Float, y: Float) {
    setPosition(x - width / 2, y - height / 2)
}
