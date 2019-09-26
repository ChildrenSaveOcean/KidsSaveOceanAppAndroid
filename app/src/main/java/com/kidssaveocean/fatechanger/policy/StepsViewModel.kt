package com.kidssaveocean.fatechanger.policy

import androidx.lifecycle.ViewModel

class StepsViewModel: ViewModel(){
    val steps = PolicyStepsRepo.getSteps()
}