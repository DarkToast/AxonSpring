package de.tarent.axon

import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.common.jpa.EntityManagerProvider
import org.axonframework.common.jpa.SimpleEntityManagerProvider
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManager

@Configuration
@EnableJpaRepositories
open class AppConfiguration {

    @Bean
    open fun axonEntityManagerProvider(manager: EntityManager): EntityManagerProvider {
        return SimpleEntityManagerProvider(manager)
    }

    @Bean
    open fun axonTransactionManager(manager: PlatformTransactionManager): TransactionManager {
        return SpringTransactionManager(manager)
    }

    @Bean
    open fun eventBus(axonEntityManagerProvider: EntityManagerProvider, axonTransactionManager: TransactionManager): EventStore {
        return EmbeddedEventStore(JpaEventStorageEngine(axonEntityManagerProvider, axonTransactionManager))
    }

    @Bean
    open fun taskRepository(eventBus: EventStore): EventSourcingRepository<TicTacToeGame> {
        return EventSourcingRepository(TicTacToeGame::class.java, eventBus)
    }
}

