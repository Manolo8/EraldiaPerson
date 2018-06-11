package com.github.manolo8.eraldiaperson.item;

import com.github.manolo8.simplecraft.data.cache.Cache;
import com.github.manolo8.simplecraft.data.connection.Database;
import com.github.manolo8.simplecraft.data.model.base.BaseDAO;
import com.github.manolo8.simplecraft.data.model.base.BaseLoader;
import com.github.manolo8.simplecraft.data.model.base.BaseRepository;
import com.github.manolo8.simplecraft.data.model.base.DTO;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRepository extends BaseRepository<Item, ItemRepository.ItemDTO, ItemRepository.ItemDAO, ItemRepository.ItemCache, ItemRepository.ItemLoader> {


    public ItemRepository(Database database) throws SQLException {
        super(database);
    }

    @Override
    protected ItemDAO initDao() throws SQLException {
        return new ItemDAO(database);
    }

    @Override
    protected ItemLoader initLoader() {
        return new ItemLoader();
    }

    @Override
    protected ItemCache initCache() {
        return new ItemCache();
    }

    public Item findOrCreate(ItemStack itemStack) throws SQLException {

        itemStack.setAmount(1);

        Item e = cache.getIfMatchHash(itemStack.hashCode());

        if (e != null) return e;

        ItemDTO o = dao.findByHash(itemStack.hashCode(),itemStack.getTypeId());

        if (o != null) return fromDTO(o);

        Item item = new Item();
        item.set(itemStack);
        item.setHash(itemStack.hashCode());

        return create(loader.toDTO(item));
    }
    //======================================================
    //=====================_REPOSITORY======================
    //======================================================


    //======================================================
    //==========================DTO=========================
    //======================================================
    class ItemDTO extends DTO {

        private String item;
        private int type;
        private int hash;
    }
    //======================================================
    //==========================DTO=========================
    //======================================================


    //======================================================
    //==========================DAO=========================
    //======================================================
    class ItemDAO extends BaseDAO<ItemDTO> {

        //Usar hash e type só para que seja mais único
        private final String findByHashQuery = "SELECT * FROM Items WHERE hash=? AND type=?";

        protected ItemDAO(Database database) throws SQLException {
            super(database, "Items", ItemDTO.class);
        }

        @Override
        protected ItemDTO newInstance() {
            return new ItemDTO();
        }

        public ItemDTO findByHash(int hash, int type) throws SQLException {
            PreparedStatement statement = prepareStatement(findByHashQuery);

            statement.setInt(1, hash);
            statement.setInt(2, type);

            ResultSet result = statement.executeQuery();

            ItemDTO dto = result.next() ? fromResult(result) : null;

            statement.close();

            return dto;
        }
    }
    //======================================================
    //=========================_DAO=========================
    //======================================================


    //======================================================
    //=========================CACHE========================
    //======================================================
    class ItemCache extends Cache<Item> {

        public Item getIfMatchHash(int hash) {
            for (Item e : cached)
                if (e.getHash() == hash) {
                    e.refreshLastCheck();
                    return e;
                }
            return null;
        }
    }
    //======================================================
    //========================_CACHE========================
    //======================================================


    //======================================================
    //=========================LOADER=======================
    //======================================================
    class ItemLoader extends BaseLoader<Item, ItemDTO> {

        private final Yaml yaml;

        public ItemLoader() {
            this.yaml = new Yaml(new YamlBukkitConstructor(), new YamlRepresenter(), new DumperOptions());
        }

        @Override
        public Item newEntity() {
            return new Item();
        }

        @Override
        public ItemDTO newDTO() {
            return new ItemDTO();
        }

        @Override
        public Item fromDTO(ItemDTO dto) throws SQLException {
            Item item = super.fromDTO(dto);

            item.set(dto.item == null || dto.item.isEmpty() ? null : yaml.loadAs(dto.item, ItemStack.class));
            item.setHash(dto.hash);

            return item;
        }

        @Override
        public ItemDTO toDTO(Item entity) throws SQLException {
            ItemDTO dto = super.toDTO(entity);

            dto.hash = entity.getHash();
            dto.item = yaml.dump(entity.get());
            dto.id = entity.getType();

            return dto;
        }

        private class YamlBukkitConstructor extends YamlConstructor {
            public YamlBukkitConstructor() {
                this.yamlConstructors.put(new Tag(Tag.PREFIX + "org.bukkit.inventory.ItemStack"), yamlConstructors.get(Tag.MAP));
            }
        }
    }
    //======================================================
    //=========================_LOADER======================
    //======================================================
}
