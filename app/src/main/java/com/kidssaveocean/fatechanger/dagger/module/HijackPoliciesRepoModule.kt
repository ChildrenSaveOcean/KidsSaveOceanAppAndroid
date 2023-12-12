package com.kidssaveocean.fatechanger.dagger.module

import com.kidssaveocean.fatechanger.firebase.model.*
import com.kidssaveocean.fatechanger.firebase.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//todo fix
abstract class HijackPoliciesRepoModule {

    companion object {
        @JvmStatic
        @Provides
        fun getUserRepo(): UsersRepo {
            return UsersRepo
        }

        @JvmStatic
        @Provides
        fun getUserModel(campaign: Campaign, dash_joined_a_policy_hijack_campaign: Boolean, dash_learn_about_problem: Boolean,
                         dash_protest: Boolean, dash_share: Boolean,
                         dash_wrote_a_letter_about_climate: Boolean, dash_wrote_a_letter_about_plastic: Boolean, hijack_policy_selected: String, location_id: String, signatures_pledged: Int,
                         user_letters_written: Int = 0, user_person_type: Int): UsersModel {
            return UsersModel(campaign, dash_joined_a_policy_hijack_campaign, dash_learn_about_problem, dash_protest, dash_share, dash_wrote_a_letter_about_climate, dash_wrote_a_letter_about_plastic, hijack_policy_selected, location_id, signatures_pledged, user_letters_written, user_person_type)
        }

        @JvmStatic
        @Provides
        fun getPoliciesRepo(): HijackPoliciesRepo {
            return HijackPoliciesRepo
        }

        @JvmStatic
        @Provides
        fun getPoliciesModel(description: String, summary: String, vote: Int): HijackPoliciesModel {
            return HijackPoliciesModel(description, summary, vote)
        }

        @JvmStatic
        @Provides
        fun getCampaignsRepo(): CampaignsRepo {
            return CampaignsRepo
        }

        @JvmStatic
        @Provides
        fun getCampaignsModel(hijack_policy: String, live: Boolean,
                              location_id: String, signatures_collected: Int, signatures_required: Int): CampaignsModel {
            return CampaignsModel(hijack_policy, live, location_id, signatures_collected, signatures_required)
        }


        @JvmStatic
        @Provides
        fun getPolicyLocationRepo(): HijackPolicyLocationRepo {
            return HijackPolicyLocationRepo
        }

        @JvmStatic
        @Provides
        fun getPolicyLocationModel(location: String): HijackPolicyLocationModel {
            return HijackPolicyLocationModel(location)
        }

        @JvmStatic
        @Provides
        fun getPolicyStepsRepo(): PolicyStepsRepo{
            return PolicyStepsRepo
        }

        @JvmStatic
        @Provides
        fun getCountriesRepo(): CountriesRepo {
            return CountriesRepo
        }

        @JvmStatic
        @Provides
        fun getCountryModel(country_code : String,
                            country_name : String,
                            country_number : Long,
                            country_address : String,
                            country_head_of_state_title : String,
                            latitude : Double,
                            longitude : Double,
                            letters_written_to_country : Long): CountryModel {
            return CountryModel(country_code, country_name, country_number, country_address, country_head_of_state_title, latitude, longitude, letters_written_to_country)
        }


        @JvmStatic
        @Provides
        fun getActionsRepo(): ActionsRepo {
            return ActionsRepo
        }

        @JvmStatic
        @Provides
        fun getActionModel(action_description: String, action_link: String, action_location: String): ActionModel {
            return ActionModel(action_description, action_link, action_location)
        }
    }
}