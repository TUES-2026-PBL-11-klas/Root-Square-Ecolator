package com.rootsquare.ecolator.initializer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rootsquare.ecolator.constants.DietConstants;
import com.rootsquare.ecolator.constants.TransportConstants;
import com.rootsquare.ecolator.constants.WaterBottleConstants;
import com.rootsquare.ecolator.model.PollutionConstant;
import com.rootsquare.ecolator.repository.DietTypeRepository;
import com.rootsquare.ecolator.repository.PollutionConstantRepository;
import com.rootsquare.ecolator.repository.TransportTypeRepository;
import com.rootsquare.ecolator.repository.WaterBottleTypeRepository;

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

        verify(dietRepo, times(DietConstants.ALL.size())).save(any());
        verify(transportRepo, times(TransportConstants.ALL.size())).save(any());
        verify(bottleRepo, times(WaterBottleConstants.ALL.size())).save(any());
        verify(pollutionRepo).save(any(PollutionConstant.class));
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
