package com.lemonade.sample

import com.lemonade.android_boilerplate.viewmodel.State
import com.lemonade.android_boilerplate.viewmodel.StateViewModel

class ImportingTestState: State()

class ImportingTestViewModel: StateViewModel<ImportingTestState>(
    ImportingTestState()
) {

    init {
        setState { getState.value!! }
    }
}