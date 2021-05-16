package com.topha.common.backends.core

interface IProvider {
    fun getSubs(): Iterable<SubData>
}
