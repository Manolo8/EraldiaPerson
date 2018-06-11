package com.github.manolo8.eraldiaperson.itemmapper;

import com.github.manolo8.eraldiaperson.Person;
import com.github.manolo8.eraldiaperson.item.ItemRepository;
import com.github.manolo8.simplecraft.data.cache.SaveCache;
import com.github.manolo8.simplecraft.data.connection.Database;
import com.github.manolo8.simplecraft.data.model.base.BaseDAO;
import com.github.manolo8.simplecraft.data.model.base.BaseLoader;
import com.github.manolo8.simplecraft.data.model.base.BaseRepository;
import com.github.manolo8.simplecraft.data.model.base.DTO;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class ItemMapperRepository extends BaseRepository<ItemMapper,
        ItemMapperRepository.ItemMapperDTO,
        ItemMapperRepository.ItemMapperDAO,
        ItemMapperRepository.ItemMapperCache,
        ItemMapperRepository.ItemMapperLoader> {

    //======================================================
    //======================REPOSITORY======================
    //======================================================
    private final ItemRepository itemRepository;

    public ItemMapperRepository(Database database, ItemRepository itemRepository) throws SQLException {
        super(database);

        this.itemRepository = itemRepository;
    }

    @Override
    protected ItemMapperDAO initDao() throws SQLException {
        return new ItemMapperDAO(database);
    }

    @Override
    protected ItemMapperLoader initLoader() {
        return new ItemMapperLoader();
    }

    @Override
    protected ItemMapperCache initCache() {
        return new ItemMapperCache(this);
    }

    public ItemMapper create(Person holder, ItemStack item) throws SQLException {
        ItemMapperDTO dto = new ItemMapperDTO();

        dto.itemId = item.getTypeId();
        dto.amount = item.getAmount();
        dto.durability = item.getDurability();

        //Reseta algumas características
        item.setDurability((short) 0);
        item.setAmount(1);

        dto.itemId = itemRepository.findOrCreate(item).getId();
        dto.personId = holder.getId();

        return create(dto);
    }
    //======================================================
    //=====================_REPOSITORY======================
    //======================================================


    //======================================================
    //==========================DTO=========================
    //======================================================
    class ItemMapperDTO extends DTO {

        private int itemId;
        private short durability;
        private int amount;

        private int index;
        private int personId;
    }
    //======================================================
    //=========================_DTO=========================
    //======================================================


    //======================================================
    //==========================DAO=========================
    //======================================================
    class ItemMapperDAO extends BaseDAO<ItemMapperDTO> {

        ItemMapperDAO(Database database) throws SQLException {
            super(database, "ItemMappers", ItemMapperDTO.class);
        }

        @Override
        protected ItemMapperDTO newInstance() {
            return new ItemMapperDTO();
        }
    }
    //======================================================
    //=========================_DAO=========================
    //======================================================

    //======================================================
    //==========================CACHE=======================
    //======================================================
    class ItemMapperCache extends SaveCache<ItemMapper, ItemMapperRepository> {

        ItemMapperCache(ItemMapperRepository repository) {
            super(repository);
        }
    }
    //======================================================
    //=========================_CACHE=======================
    //======================================================


    //======================================================
    //=========================LOADER=======================
    //======================================================
    class ItemMapperLoader extends BaseLoader<ItemMapper, ItemMapperDTO> {

        @Override
        public ItemMapper newEntity() {
            return new ItemMapper();
        }

        @Override
        public ItemMapperDTO newDTO() {
            return new ItemMapperDTO();
        }

        @Override
        public ItemMapper fromDTO(ItemMapperDTO dto) throws SQLException {
            ItemMapper entity = super.fromDTO(dto);

            entity.setIndex(dto.index);

            ItemStack stack = itemRepository.findOne(dto.itemId).get();
            stack.setDurability(dto.durability);
            stack.setAmount(dto.amount);

            entity.setPersonId(dto.personId);
            entity.setItem(stack);

            return entity;
        }

        @Override
        public ItemMapperDTO toDTO(ItemMapper entity) throws SQLException {
            ItemMapperDTO dto = super.toDTO(entity);

            dto.index = entity.getIndex();

            ItemStack item = entity.getItem();

            dto.durability = item.getDurability();
            dto.amount = item.getAmount();

            //Reseta algumas características
            item.setDurability((short) 0);
            item.setAmount(1);

            dto.itemId = itemRepository.findOrCreate(item).getId();
            dto.personId = entity.getPersonId();

            return dto;
        }
    }
    //======================================================
    //========================_LOADER=======================
    //======================================================
}
