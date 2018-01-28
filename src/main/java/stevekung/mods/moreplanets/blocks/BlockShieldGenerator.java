package stevekung.mods.moreplanets.blocks;

import micdoodle8.mods.galacticraft.core.blocks.BlockAdvancedTile;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import stevekung.mods.moreplanets.core.MorePlanetsCore;
import stevekung.mods.moreplanets.tileentity.TileEntityShieldGenerator;
import stevekung.mods.moreplanets.util.ItemDescription;
import stevekung.mods.moreplanets.util.blocks.EnumSortCategoryBlock;
import stevekung.mods.moreplanets.util.blocks.IBlockDescription;
import stevekung.mods.moreplanets.util.blocks.ISingleBlockRender;
import stevekung.mods.moreplanets.util.blocks.ISortableBlock;
import stevekung.mods.moreplanets.util.helper.BlockStateHelper;
import stevekung.mods.moreplanets.util.helper.ItemDescriptionHelper;

public class BlockShieldGenerator extends BlockAdvancedTile implements ISortableBlock, ISingleBlockRender, IBlockDescription
{
    public BlockShieldGenerator(String name)
    {
        super(Material.IRON);
        this.setHardness(5.0F);
        this.setUnlocalizedName(name);
        this.setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStateHelper.FACING_HORIZON, EnumFacing.NORTH));
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return MorePlanetsCore.BLOCK_TAB;
    }

    @Override
    public boolean onMachineActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        player.openGui(MorePlanetsCore.INSTANCE, -1, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public boolean onUseWrench(World world, BlockPos pos, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        int change = world.getBlockState(pos).getValue(BlockStateHelper.FACING_HORIZON).rotateY().getHorizontalIndex();
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityShieldGenerator)
        {
            int direction = 0;

            if (change == 0)
            {
                direction = 180;
            }
            if (change == 1)
            {
                direction = -90;
            }
            if (change == 2)
            {
                direction = 0;
            }
            if (change == 3)
            {
                direction = 90;
            }
            TileEntityShieldGenerator shield = (TileEntityShieldGenerator) tile;
            shield.setFacing(direction);
        }
        world.setBlockState(pos, this.getStateFromMeta(change), 3);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemStack)
    {
        int angle = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = EnumFacing.getHorizontal(angle).getOpposite().getHorizontalIndex();
        int direction = 0;

        if (change == 0)
        {
            direction = 180;
        }
        if (change == 1)
        {
            direction = -90;
        }
        if (change == 2)
        {
            direction = 0;
        }
        if (change == 3)
        {
            direction = 90;
        }

        world.setBlockState(pos, this.getDefaultState().withProperty(BlockStateHelper.FACING_HORIZON, placer.getHorizontalFacing().getOpposite()));
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityShieldGenerator)
        {
            TileEntityShieldGenerator shield = (TileEntityShieldGenerator) tile;
            shield.setFacing(direction);
            shield.onCreate(world, pos);

            if (itemStack.hasTagCompound())
            {
                NBTTagCompound nbt = itemStack.getTagCompound();
                shield.storage.setEnergyStored(nbt.getFloat("EnergyStored"));
                shield.shieldSize = nbt.getFloat("ShieldSize");
                shield.maxShieldSize = nbt.getInteger("MaxShieldSize");
                shield.shieldDamage = nbt.getInteger("ShieldDamage");
                shield.shieldCapacity = nbt.getInteger("ShieldCapacity");
                shield.maxShieldCapacity = nbt.getInteger("MaxShieldCapacity");
                shield.shieldChargeCooldown = nbt.getInteger("ShieldChargeCooldown");
                shield.needCharged = nbt.getBoolean("NeedCharged");
                shield.enableShield = nbt.getBoolean("EnableShield");
                shield.enableDamage = nbt.getBoolean("EnableDamage");
            }
            if (placer instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) placer;
                shield.ownerUUID = player.getGameProfile().getId().toString();
            }
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile, ItemStack heldStack)
    {
        player.addExhaustion(0.025F);

        if (tile instanceof TileEntityShieldGenerator)
        {
            ItemStack machine = new ItemStack(this);
            TileEntityShieldGenerator shield = (TileEntityShieldGenerator) tile;
            NBTTagCompound nbt = new NBTTagCompound();

            nbt.setFloat("ShieldSize", shield.shieldSize);
            nbt.setInteger("MaxShieldSize", shield.maxShieldSize);
            nbt.setInteger("ShieldDamage", shield.shieldDamage);
            nbt.setInteger("ShieldCapacity", shield.shieldCapacity);
            nbt.setInteger("MaxShieldCapacity", shield.maxShieldCapacity);
            nbt.setInteger("ShieldChargeCooldown", shield.shieldChargeCooldown);
            nbt.setBoolean("NeedCharged", shield.needCharged);
            nbt.setBoolean("EnableShield", shield.enableShield);
            nbt.setBoolean("EnableDamage", shield.enableDamage);

            if (shield.getEnergyStoredGC() > 0)
            {
                nbt.setFloat("EnergyStored", shield.getEnergyStoredGC());
            }
            machine.setTagCompound(nbt);
            Block.spawnAsEntity(world, pos, machine);
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityShieldGenerator)
        {
            ((TileEntityShieldGenerator) tile).onDestroy(tile);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityShieldGenerator();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.getHorizontal(meta % 4);
        return this.getDefaultState().withProperty(BlockStateHelper.FACING_HORIZON, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BlockStateHelper.FACING_HORIZON).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BlockStateHelper.FACING_HORIZON);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rotation)
    {
        return state.withProperty(BlockStateHelper.FACING_HORIZON, rotation.rotate(state.getValue(BlockStateHelper.FACING_HORIZON)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror)
    {
        return state.withRotation(mirror.toRotation(state.getValue(BlockStateHelper.FACING_HORIZON)));
    }

    @Override
    public EnumSortCategoryBlock getBlockCategory(int meta)
    {
        return EnumSortCategoryBlock.MACHINE_NON_BLOCK;
    }

    @Override
    public String getName()
    {
        return "shield_generator";
    }

    @Override
    public ItemDescription getDescription()
    {
        return (itemStack, list) -> list.addAll(ItemDescriptionHelper.getDescription(BlockShieldGenerator.this.getUnlocalizedName() + ".description"));
    }
}