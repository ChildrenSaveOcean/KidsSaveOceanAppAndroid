package com.kidssaveocean.fatechanger

import android.os.Bundle
import com.kidssaveocean.fatechanger.common.AbstractActivity
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel

/**
 * this class explain how to get data from the Firebase.
 */
class FirebaseGetDataInAct : AbstractActivity() {
    lateinit var list: List<Pair<String, HijackPoliciesModel>>

    //todo delete this class after firebase usage is fixed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * For example, if we want to get the data for hijack policies from Firebase, use
         * {@link com.kidssaveocean.fatechanger.firebase.repository.HijackPoliciesRepo } class
         * As this is an async operation, here we use RxJava to subscribe the data. Like:
         * XXXRepo.getDataObservable().subscribe(). The observable will return the data in the callback.
         * This method will return a disposable, need to dispose it onDestroy(),
         * Here we can directly call disposableOnDestroy(), under the hood it will handle the disposables.
         */
//        disposableOnDestroy(HijackPoliciesRepo.getData().subscribe({ data ->
//            /**
//             * Success callback. Will return the data we request.
//             * The return type of the data depends on the business requirements.
//             */
//            list = data
//        }, {
//
//            /**
//             * it.message is the reason why the request fails.
//             * Here could handle the error message.
//             */
//            it.message
//        }))
    }
}