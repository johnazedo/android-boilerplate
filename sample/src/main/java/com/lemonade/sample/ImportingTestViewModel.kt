package com.lemonade.sample

import com.lemonade.kotlin_mvvm.viewmodel.State
import com.lemonade.kotlin_mvvm.viewmodel.StateViewModel

class ImportingTestState: State()

class ImportingTestViewModel: StateViewModel<ImportingTestState>(
    ImportingTestState()
) {

    init {
        setState { getState.value!! }
    }
}