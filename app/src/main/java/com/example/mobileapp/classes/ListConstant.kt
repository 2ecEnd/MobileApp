package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class ListConstant(
    override var scope: NewScope,
    var value: Value.LIST = Value.LIST()
) : Block()
{
    var blockList: MutableList<Block> = mutableStateListOf()
    var addBlockRect: Rect = Rect.Zero

    override fun execute() : Value
    {
        value = Value.LIST(mutableStateListOf())

        for (block in blockList)
        {
            when (block)
            {
                is Constant -> value.value.add(block.execute())
                is MathExpression -> value.value.add(block.execute())
                is BoolExpression -> value.value.add(block.execute())
                is UseVariable -> value.value.add(block.execute())
                is UseListElement -> value.value.add(block.execute())
                is ListConstant -> value.value.add(block.execute())
                else ->
                {
                    isTroublesome = true
                    throw Exception(R.string.unknown_error.toString())
                }
            }
        }

        return value
    }
}