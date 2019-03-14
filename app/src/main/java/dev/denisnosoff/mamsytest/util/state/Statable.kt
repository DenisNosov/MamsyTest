package dev.denisnosoff.mamsytest.util.state

interface Statable {
    var state: State

    fun changeUI(state: State)
}