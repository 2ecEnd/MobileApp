package com.example.mobileapp.composables

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.R
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.SetListElement
import com.example.mobileapp.ui.theme.*

@Composable
fun DrawSetListElement(
    block: SetListElement,
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    isActive: Boolean
) {
    block.value.parent = block
    block.index.parent = block
    if (block.source != null) block.source!!.parent = block

    Card(
        modifier = Modifier
            .wrapContentSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset -> onDragStart(offset, block) },
                    onDrag = { _, _ -> },
                    onDragEnd = { onDragEnd(block) }
                )
            }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .onGloballyPositioned { coordinates ->
                block.selfRect = coordinates.boundsInWindow()
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = BlockColor),
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        block.sourceRect = coordinates.boundsInWindow()
                    }
            ) {
                key(block.source) {
                    if (block.source != null) {
                        DrawBlock(block.source as Block, onDragStart, onDragEnd, isActive)
                    }
                    else{
                        Card(
                            modifier = Modifier
                                .height(38.dp)
                                .width(56.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = AddValueOpportunityColor
                            )
                        ){}
                    }
                }
            }

            Text(
                stringResource(R.string.left_square_bracket),
                fontSize = 36.sp,
                color = White,
                modifier = Modifier.padding(start = 8.dp)
            )
            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        block.indexRect = coordinates.boundsInWindow()
                    }
            ) {
                key(block.source) {
                    DrawBlock(block.index, onDragStart, onDragEnd, isActive)
                }
            }
            Text(
                stringResource(R.string.right_square_bracket),
                fontSize = 36.sp,
                color = White,
                modifier = Modifier.padding(start = 0.dp)
            )

            Text(
                stringResource(R.string.assign),
                fontSize = 36.sp,
                color = White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        block.valueRect = coordinates.boundsInWindow()
                    }
            ) {
                key(block.value) {
                    DrawBlock(block.value, onDragStart, onDragEnd, isActive)
                }
            }
        }
    }
}
