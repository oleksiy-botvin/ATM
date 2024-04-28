package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.data.entity.arg.CheckPINArg;
import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;

public interface ATMWorker {

    @NonNull Observable<ATMState> observeState();
    @NonNull Single<Card> checkCard(@NonNull Card arg);
    @NonNull Completable checkPIN(@NonNull CheckPINArg arg);
    @NonNull Single<OperationResult> performOperation(@NonNull Operation operation);
}
