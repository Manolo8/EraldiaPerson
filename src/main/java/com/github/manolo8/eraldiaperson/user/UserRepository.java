package com.github.manolo8.eraldiaperson.user;

import com.github.manolo8.simplecraft.data.connection.Database;
import com.github.manolo8.simplecraft.data.model.named.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepository extends NamedRepository<User,
        UserRepository.UserDTO,
        UserRepository.UserDAO,
        UserRepository.UserCache,
        UserRepository.UserLoader> {

    //======================================================
    //======================REPOSITORY======================
    //======================================================
    public UserRepository(Database database) throws SQLException {
        super(database);
    }

    @Override
    protected UserDAO initDao() throws SQLException {
        return new UserDAO(database);
    }

    @Override
    protected UserLoader initLoader() {
        return new UserLoader();
    }

    @Override
    protected UserCache initCache() {
        return new UserCache(this);
    }

    public User findOne(UUID uuid) throws SQLException {
        User user = cache.getIfMatch(uuid);

        if (user != null) return user;

        UserDTO dto = dao.findOne(uuid);

        return fromDTO(dto);
    }

    public User findOrCreate(UUID uuid) throws SQLException {
        User user = findOne(uuid);

        if (user != null) return user;

        UserDTO dto = new UserDTO();

        dto.leastSigBits = uuid.getLeastSignificantBits();
        dto.mostSigBits = uuid.getMostSignificantBits();

        user = create(dto);

        return user;
    }
    //======================================================
    //=====================_REPOSITORY======================
    //======================================================


    //======================================================
    //==========================DTO=========================
    //======================================================
    class UserDTO extends NamedDTO {

        private long mostSigBits;
        private long leastSigBits;

    }
    //======================================================
    //=========================_DTO=========================
    //======================================================


    //======================================================
    //==========================DAO=========================
    //======================================================
    class UserDAO extends NamedDAO<UserDTO> {

        private final String findOneByUUID = "SELECT id FROM Users WHERE mostSigBits=? AND leastSigBits=?";

        protected UserDAO(Database database) throws SQLException {
            super(database, "users", UserDTO.class);
        }

        public UserDTO findOne(UUID uuid) throws SQLException {
            PreparedStatement statement = prepareStatement(findOneByUUID);

            statement.setLong(1, uuid.getMostSignificantBits());
            statement.setLong(2, uuid.getLeastSignificantBits());

            ResultSet result = statement.executeQuery();

            UserDTO dto = result.next() ? fromResult(result) : null;

            result.close();

            return dto;
        }

        @Override
        protected UserDTO newInstance() {
            return new UserDTO();
        }
    }
    //======================================================
    //=========================_DAO=========================
    //======================================================

    //======================================================
    //==========================CACHE=======================
    //======================================================
    class UserCache extends NamedCache<User, UserRepository> {

        UserCache(UserRepository repository) {
            super(repository);
        }

        public User getIfMatch(UUID uuid) {
            for (User user : getCached()) {
                if (user.getUuid().equals(uuid)) return user;
            }

            return null;
        }
    }
    //======================================================
    //=========================_CACHE=======================
    //======================================================


    //======================================================
    //=========================LOADER=======================
    //======================================================
    class UserLoader extends NamedLoader<User, UserDTO> {

        @Override
        public User newEntity() {
            return new User();
        }

        @Override
        public UserDTO newDTO() {
            return new UserDTO();
        }

        @Override
        public User fromDTO(UserDTO dto) throws SQLException {
            User entity = super.fromDTO(dto);

            entity.setName(dto.name);
            entity.setUuid(new UUID(dto.mostSigBits, dto.leastSigBits));

            return entity;
        }

        @Override
        public UserDTO toDTO(User entity) throws SQLException {
            UserDTO dto = super.toDTO(entity);

            dto.mostSigBits = entity.getUuid().getMostSignificantBits();
            dto.leastSigBits = entity.getUuid().getLeastSignificantBits();

            return dto;
        }
    }
    //======================================================
    //========================_LOADER=======================
    //======================================================
}
