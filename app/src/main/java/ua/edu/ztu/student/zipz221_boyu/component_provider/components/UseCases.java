package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;

public interface UseCases {

    @NonNull
    WithoutArgUseCase<Single<List<Account>>> getAllAccounts();
    @NonNull
    WithoutArgUseCase<Completable> checkReadinessForWork();
    @NonNull
    WithoutArgUseCase<Observable<Long>> requestMaintenance();
    @NonNull
    WithArgUseCase<Card, Single<Card>> checkCard();
}
