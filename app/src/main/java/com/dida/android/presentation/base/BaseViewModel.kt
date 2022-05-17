package com.dida.android.presentation.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    /**
     * RxJava 의 observing을 위한 부분.
     * addDisposable을 이용하여 추가하기만 하면 된다
     *
     * RxJava를 사용할 때 스트림을 취소해주지 않으면 계속 돌기 때문에 위험하다. (메모리 릭 발생)
     * 이를 해결하기 위해서는 CompositeDisposable이라는 어레이 스트림에 만들어진 코드를 넣어두고 한 번에 취소하는 방법을 사용한다.
     * CompositeDisposable 변수를 선언하고 생명 주기 변경이 일어나면 clear로 지워준다.
     */
//    private val compositeDisposable = CompositeDisposable()
//
//    fun addDisposable(disposable: Disposable) {
//        compositeDisposable.add(disposable)
//    }
//
//    override fun onCleared() {
//        compositeDisposable.clear()
//        super.onCleared()
//    }
}