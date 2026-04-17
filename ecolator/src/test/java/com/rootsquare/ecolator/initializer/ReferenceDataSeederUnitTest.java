package com.rootsquare.ecolator.initializer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rootsquare.ecolator.repository.DietTypeRepository;
import com.rootsquare.ecolator.repository.PollutionConstantRepository;
import com.rootsquare.ecolator.repository.TransportTypeRepository;
import com.rootsquare.ecolator.repository.WaterBottleTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferenceDataSeederUnitTest {

    @Mock
    private DietTypeRepository dietRepo;

    @Mock
    private TransportTypeRepository transportRepo;

    @Mock
    private WaterBottleTypeRepository bottleRepo;

    @Mock
    private PollutionConstantRepository pollutionRepo;

    @InjectMocks
    private ReferenceDataSeeder seeder;

    @Test
    void runSeedsAllReferenceDataWhenRepositoriesAreEmpty() {
        when(dietRepo.count()).thenReturn(0L);
        when(transportRepo.count()).thenReturn(0L);
        when(bottleRepo.count()).thenReturn(0L);
        when(pollutionRepo.count()).thenReturn(0L);

        seeder.run();

        verify(dietRepo).save(any());
        verify(transportRepo).save(any());
        verify(bottleRepo).save(any());
        verify(pollutionRepo).save(any());
    }

    @Test
    void runDoesNotSeedWhenDataAlreadyExists() {
        when(dietRepo.count()).thenReturn(1L);
        when(transportRepo.count()).thenReturn(1L);
        when(bottleRepo.count()).thenReturn(1L);
        when(pollutionRepo.count()).thenReturn(1L);

        seeder.run();

        verify(dietRepo, never()).save(any());
        verify(transportRepo, never()).save(any());
        verify(bottleRepo, never()).save(any());
        verify(pollutionRepo, never()).save(any());
    }
}
