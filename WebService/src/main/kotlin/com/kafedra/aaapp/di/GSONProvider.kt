package com.kafedra.aaapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.inject.Provider

class GSONProvider : Provider<Gson> {
    override fun get(): Gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create()
}
