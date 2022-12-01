package com.example.listcards.core

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.listcards.utils.MainCoroutineRule
import org.junit.rules.RuleChain

val instantLiveDataAndCoroutinesRules: RuleChain
    get() = RuleChain
        .outerRule(MainCoroutineRule())
        .around(InstantTaskExecutorRule())