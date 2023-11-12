package com.vlv.extensions

import android.view.ViewGroup
import androidx.transition.TransitionManager
import br.com.arch.toolkit.statemachine.StateMachine
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.state

enum class State {
    EMPTY,
    DATA,
    LOADING,
    ERROR,
    INITIAL
}

fun ViewStateMachine.dataState() = apply {
    changeState(State.DATA.ordinal)
}

fun ViewStateMachine.errorState() = apply {
    changeState(State.ERROR.ordinal)
}

fun ViewStateMachine.loadingState() = apply {
    changeState(State.LOADING.ordinal)
}

fun ViewStateMachine.emptyState() = apply {
    changeState(State.EMPTY.ordinal)
}

fun ViewStateMachine.initialState() = apply {
    changeState(State.INITIAL.ordinal)
}

fun ViewStateMachine.isInInitialState() = this.currentStateKey == State.INITIAL.ordinal

fun StateMachine<ViewStateMachine.State>.defaultConfig(
    root: ViewGroup,
    initial: State = State.LOADING
) = apply {
    config {
        initialState = initial.ordinal
        onChangeState = StateMachine.OnChangeStateCallback {
            TransitionManager.beginDelayedTransition(root)
        }
    }
}

fun StateMachine<ViewStateMachine.State>.stateData(func: ViewStateMachine.State.() -> Unit) = apply {
    state(State.DATA.ordinal, func)
}

fun StateMachine<ViewStateMachine.State>.stateLoading(func: ViewStateMachine.State.() -> Unit) = apply {
    state(State.LOADING.ordinal, func)
}

fun StateMachine<ViewStateMachine.State>.stateError(func: ViewStateMachine.State.() -> Unit) = apply {
    state(State.ERROR.ordinal, func)
}

fun StateMachine<ViewStateMachine.State>.stateInitial(func: ViewStateMachine.State.() -> Unit) = apply {
    state(State.INITIAL.ordinal, func)
}

fun StateMachine<ViewStateMachine.State>.stateEmpty(func: ViewStateMachine.State.() -> Unit) = apply {
    state(State.EMPTY.ordinal, func)
}
