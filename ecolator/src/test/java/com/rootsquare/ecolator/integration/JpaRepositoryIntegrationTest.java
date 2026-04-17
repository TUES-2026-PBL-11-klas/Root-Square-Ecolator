package com.rootsquare.ecolator.integration;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.rootsquare.ecolator.model.DietTypes;
import com.rootsquare.ecolator.model.PollutionConstant;
import com.rootsquare.ecolator.model.TransportType;
import com.rootsquare.ecolator.model.UserPollution;
import com.rootsquare.ecolator.model.WaterBottleType;
import com.rootsquare.ecolator.repository.DietTypeRepository;
import com.rootsquare.ecolator.repository.PollutionConstantRepository;
import com.rootsquare.ecolator.repository.TransportTypeRepository;
import com.rootsquare.ecolator.repository.UserPollutionRepository;
import com.rootsquare.ecolator.repository.WaterBottleTypeRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaRepositoryIntegrationTest {

    @Autowired
    private UserPollutionRepository userPollutionRepository;

    @Autowired
    private DietTypeRepository dietTypeRepository;

    @Autowired
    private TransportTypeRepository transportTypeRepository;

    @Autowired
    private WaterBottleTypeRepository waterBottleTypeRepository;

    @Autowired
    private PollutionConstantRepository pollutionConstantRepository;

    @Test
    void repositoriesCanSaveAndRetrieveEntities() {
        UserPollution pollution = UserPollution.builder()
                .transportValue(5.0f)
                .waterValue(3.0f)
                .totalMonthlyCoefficient(8.0f)
                .build();
        userPollutionRepository.save(pollution);

        DietTypes dietTypes = DietTypes.builder()
                .name("TEST_DIET")
                .value(1.0f)
                .build();
        dietTypeRepository.save(dietTypes);

        TransportType transportType = TransportType.builder()
                .name("TEST_TRANSPORT")
                .value(2.0f)
                .build();
        transportTypeRepository.save(transportType);

        WaterBottleType waterBottleType = WaterBottleType.builder()
                .name("TEST_BOTTLE")
                .value(0.5f)
                .build();
        waterBottleTypeRepository.save(waterBottleType);

        PollutionConstant pollutionConstant = PollutionConstant.builder()
                .showerPerMinute(0.2f)
                .waterdrinkingPerLiterS(0.1f)
                .electricityPerKw(0.3f)
                .build();
        pollutionConstantRepository.save(pollutionConstant);

        assertThat(userPollutionRepository.findAll()).hasSize(1);
        assertThat(dietTypeRepository.findAll()).hasSize(1);
        assertThat(transportTypeRepository.findAll()).hasSize(1);
        assertThat(waterBottleTypeRepository.findAll()).hasSize(1);
        assertThat(pollutionConstantRepository.findAll()).hasSize(1);
    }
}
