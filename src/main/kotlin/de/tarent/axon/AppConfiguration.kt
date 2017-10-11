package de.tarent.axon

import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.eventsourcing.EventSourcingRepository
import org.springframework.context.annotation.Bean
import org.axonframework.eventhandling.SimpleEventBus
import org.axonframework.eventhandling.EventBus
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.springframework.context.annotation.Configuration

@Configuration
open class AppConfiguration {

    @Bean
    open fun eventBus(): EventBus {
        return SimpleEventBus()
    }

    @Bean
    open fun taskRepository(): EventSourcingRepository<TicTacToeGame> {
        val eventStore = EmbeddedEventStore(InMemoryEventStorageEngine())
        return EventSourcingRepository(TicTacToeGame::class.java, eventStore)
    }
}

