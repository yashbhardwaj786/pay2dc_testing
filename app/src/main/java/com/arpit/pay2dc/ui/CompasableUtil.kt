package com.arpit.pay2dc.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpit.pay2dc.data.Currency

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowOutlinedTextField(modifier: Modifier, onTextChange: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier,
        value = text,
        textStyle = TextStyle.Default.copy(fontSize = 28.sp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }),
        onValueChange = {
            text = it
            onTextChange(it)
        },
        label = { Text("Enter Amount") }
    )
}

@Composable
fun SpinnerSample(
    list: List<String?>,
    preselected: String?,
    onSelectionChanged: (String) -> Unit
) {
    onSelectionChanged(preselected ?: "")
    var selected by rememberSaveable { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .clickable {
                expanded = !expanded
            }
            .wrapContentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.wrapContentSize()
        ) {

            Text(
                text = selected ?: "",
                fontSize = 20.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.wrapContentWidth()
            ) {
                list.forEach { listEntry ->

                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected ?: "")
                        },
                        content = {
                            Text(
                                text = listEntry ?: "",
                                modifier = Modifier
                                    .wrapContentWidth()
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecyclerView(currencies: SnapshotStateList<Currency?>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)

    ) {
        items(items = currencies) { item ->
            ListItem(item) {
            }
        }
    }
}

@Composable
fun ListItem(cur: Currency?, action: (Currency) -> Unit) {
    cur?.let { item ->
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .clickable { action(item) },
            elevation = 2.dp,
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = if (item.amount > 0) {
                        String.format("%.2f", item.amount)
                    } else {
                        ""
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 16.sp,
                )
            }
        }
    }
}
